namespace Sqwads.Domain.Common;

public interface IEntity
{
    int Id { get; }
    string CreatedBy { get; }
    DateTime CreatedAt { get; }
    string? LastModifiedBy { get; }
    DateTime? LastModifiedAt { get; }
}
